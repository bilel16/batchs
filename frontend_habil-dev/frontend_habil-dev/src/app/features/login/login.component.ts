// src/app/components/login/login.component.ts
import { Component, OnInit, OnDestroy, inject, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuthService } from '../../core/services/backend/auth.service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  standalone: false,
})
export class LoginComponent implements OnInit, OnDestroy {
  form!: FormGroup;
  errorMessage = '';
  loading = false;
  animate = false;
  showPassword = false;
  usernameError = false;
  passwordError = false;
  capsLockOn = signal(false);

  nameAPP = environment.nameAPP;

  private readonly destroy$ = new Subject<void>();
  private readonly ANIMATION_DURATION = 2980;

  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly formBuilder = inject(FormBuilder);

  ngOnInit(): void {
    this.initializeForm();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private initializeForm(): void {
    this.form = this.formBuilder.nonNullable.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });

    this.form
      .get('username')
      ?.valueChanges.pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        if (this.usernameError) {
          this.usernameError = false;
          this.errorMessage = '';
        }
      });

    this.form
      .get('password')
      ?.valueChanges.pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        if (this.passwordError) {
          this.passwordError = false;
          this.errorMessage = '';
        }
      });
  }

  onSubmit(): void {
    if (this.loading) return;

    this.loading = true;
    this.errorMessage = '';
    this.usernameError = false;
    this.passwordError = false;

    const { username, password } = this.form.value;

    this.authService
      .login(username, password)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        // login() already saves token/user/menus via handleTokenResponse
        next: () => this.handleLoginSuccess(),
        error: (error) => this.handleLoginError(error),
      });
  }

  private handleLoginSuccess(): void {
    this.animate = true;

    setTimeout(() => {
      this.router
        .navigate(['dashboard'])
        .catch((err) => console.error('Navigation error:', err));
    }, this.ANIMATION_DURATION);
  }

  private handleLoginError(error: any): void {
    const message = error.error?.message;

    if (message === 'Matricule inexistante') {
      this.usernameError = true;
      this.errorMessage = 'Identifiant invalide';
    } else if (message === 'Mot de passe invalide') {
      this.passwordError = true;
      this.errorMessage = 'Mot de passe invalide';
    } else {
      this.passwordError = true;
      this.errorMessage = 'Une erreur est survenue lors de la connexion';
    }

    this.loading = false;
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onCapsLockChange(isOn: boolean): void {
    this.capsLockOn.set(isOn);
  }
}