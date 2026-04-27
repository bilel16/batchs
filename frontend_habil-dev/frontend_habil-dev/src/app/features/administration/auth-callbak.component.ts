// features/auth-callback/auth-callback.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../core/services/backend/auth.service';


@Component({
  selector: 'app-auth-callback',
  standalone: false,  template: `
    <div class="callback-container">
      <div class="spinner"></div>
      <h2>Authentication</h2>
      <p>{{ message }}</p>
      <small style="margin-top: 20px; color: #999;">If you're stuck here, check the browser console (F12)</small>
    </div>
  `,
  styles: [`
    .callback-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 100vh;
      gap: 20px;
      background-color: #f5f5f5;
    }
    h2 { color: #333; margin: 0; }
    p { color: #666; margin: 0; }
    .spinner {
      border: 4px solid #f3f3f3;
      border-top: 4px solid #3498db;
      border-radius: 50%;
      width: 40px;
      height: 40px;
      animation: spin 1s linear infinite;
    }
    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }
  `]
})
export class AuthCallbackComponent implements OnInit {
  message = 'Completing sign in...';
  private processingAuth = false; // Prevent duplicate processing

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    console.log('🏗️ AuthCallbackComponent constructor');
  }  
  
  ngOnInit(): void {
    if (this.processingAuth) {
      console.log('⚠️ Already processing authentication, skipping...');
      return;
    }
    
    this.processingAuth = true;
    console.log('========================================');
    console.log('🚀 AUTH CALLBACK - START');
    console.log('========================================');
      const code = this.route.snapshot.queryParams['code'];
    const error = this.route.snapshot.queryParams['error'];
    const state = this.route.snapshot.queryParams['state'];
    const currentUrl = window.location.origin + window.location.pathname;

    // Determine where to navigate after auth succeeds
    let targetPath = '/canevas/creer-maj-canevas'; // default
    if (state) {
      try {
        const decoded = decodeURIComponent(state);
        if (decoded.startsWith('http')) {
          targetPath = new URL(decoded).pathname;
        } else {
          targetPath = decoded;
        }
      } catch (e) {
        // keep default
      }
    }

    console.log('📋 Callback Details:', { 
      code: code ? `${code.substring(0, 15)}...` : '❌ NO CODE', 
      error: error || 'none',
      state: state || 'none',
      targetPath,
      currentUrl,
      fullUrl: window.location.href
    });

    // Handle error from portal
    if (error) {
      console.error('❌ Authentication error from portal:', error);
      this.message = 'Authentication failed. Redirecting...';
      setTimeout(() => {
        console.log('↩️ Redirecting to portal login due to error');
        this.authService.redirectToLogin();
      }, 2000);
      return;
    }

    // Handle authorization code exchange
    if (code && code.trim()) {
      console.log('✅ CODE FOUND - Starting token exchange...');
      this.message = 'Exchanging authorization code...';
      
      this.authService.exchangeCodeForTokens(code, currentUrl).subscribe({
        next: (response) => {
          console.log('✅✅✅ TOKEN EXCHANGE SUCCESS!', {
            hasToken: !!response.accessToken,
            tokenLength: response.accessToken?.length,
            hasUser: !!response.user,
            username: response.user?.username,
            hasMenus: !!response.menus
          });
          
          this.message = 'Success! Loading application...';
          
          // Verify storage before navigation
          const tokenInStorage = sessionStorage.getItem('access_token');
          const userInStorage = sessionStorage.getItem('user');
          
          console.log('🔍 Storage verification:', {
            tokenSaved: !!tokenInStorage,
            userSaved: !!userInStorage,
            menusExist: !!sessionStorage.getItem('menus')
          });
          
          if (!tokenInStorage || !userInStorage) {
            console.error('❌ Storage verification failed! Tokens not saved properly');
            this.message = 'Storage error. Retrying...';
            setTimeout(() => this.authService.redirectToLogin(), 2000);
            return;
          }
            // Navigate to the target page
          setTimeout(() => {
            console.log('🚀 Navigating to', targetPath);
            this.router.navigateByUrl(targetPath, { replaceUrl: true }).then(
              success => {
                if (success) {
                  console.log('✅ Navigation completed');
                } else {
                  console.warn('⚠️ Navigation returned false, using window.location');
                  window.location.href = targetPath;
                }
              },
              error => {
                console.error('❌ Navigation failed:', error);
                window.location.href = targetPath;
              }
            );
          }, 300);
        },
        error: (err) => {
          console.error('❌❌❌ TOKEN EXCHANGE FAILED!');
          console.error('Error details:', {
            status: err.status,
            statusText: err.statusText,
            message: err.message,
            url: err.url,
            error: err.error
          });
          
          this.message = `Authentication failed: ${err.status || 'Network error'}`;
          
          // If token exchange fails, redirect to login after delay
          setTimeout(() => {
            console.log('↩️ Redirecting to portal login due to token exchange failure');
            this.authService.redirectToLogin();
          }, 3000);
          
          console.log('========================================');
          console.log('🏁 AUTH CALLBACK - END (FAILURE)');
          console.log('========================================');
        }
      });
    } else {
      // No code - this shouldn't happen in normal SSO flow
      console.warn('⚠️⚠️⚠️ NO CODE PROVIDED!');
      console.log('Attempting SSO session check as fallback...');
      this.message = 'Checking session...';
      
      this.authService.checkSsoSession().subscribe({
        next: (response) => {
          console.log('📥 SSO check response:', response);          if (response && response.accessToken) {
            console.log('✅ Valid SSO session found, navigating...');
            this.router.navigateByUrl(targetPath, { replaceUrl: true });
          } else {
            console.log('❌ No valid SSO session');
            this.message = 'No active session. Redirecting to login...';
            setTimeout(() => {
              console.log('↩️ Redirecting to portal login - no session');
              this.authService.redirectToLogin();
            }, 2000);
          }
        },        
        error: (err) => {
          console.error('❌ SSO check failed:', err);
          this.message = 'Session check failed. Redirecting...';
          setTimeout(() => {
            console.log('↩️ Redirecting to portal login - SSO check error');
            this.authService.redirectToLogin();
          }, 2000);
        }
      });
      
      console.log('========================================');
      console.log('🏁 AUTH CALLBACK - END (NO CODE)');
      console.log('========================================');
    }
  }
}
