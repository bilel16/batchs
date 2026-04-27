/**
 * 🧪 TOAST ERROR HANDLING TEST SCENARIOS
 * 
 * This file contains test methods to verify our enhanced toast system
 * handles all dual backend error scenarios correctly.
 */

import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { SharedFrontService } from './shared-front.service';

@Injectable({
  providedIn: 'root'
})
export class ToastTestService {

  constructor(private sharedService: SharedFrontService) {}

  /**
   * Test Legacy ResponseSAGA Batch Operation Error
   * Simulates the exact error you encountered
   */
  testLegacyBatchError() {
    console.log('🧪 Testing Legacy Batch Error...');
    
    const mockLegacyBatchError = {
      error: {
        "returnCode": 1,
        "message": "Batch operation failed: 1 errors",
        "data": {
          "successful": [],
          "failed": [
            {
              "index": 0,
              "identifier": "App=BNAHABIL,Menu=ADMIN_USER_PROF,Profil=TestHabil,Tstrc=2",
              "error": "ProfilMenuApplication with id App=BNAHABIL,Menu=ADMIN_USER_PROF,Profil=TestHabil,Tstrc=2 already exists"
            }
          ],
          "mode": "ALL_OR_NOTHING",
          "partialSuccess": false
        }
      },
      status: 400,
      statusText: 'Bad Request',
      message: 'HTTP Error'
    } as HttpErrorResponse;

    this.sharedService.handleError(mockLegacyBatchError, 'Test Legacy Batch Error');
  }

  /**
   * Test Modern Global Exception Handler Error
   * Simulates the exact error you encountered  
   */
  testModernSimpleError() {
    console.log('🧪 Testing Modern Simple Error...');
    
    const mockModernError = {
      error: {
        "code": "DUPLICATE_RESOURCE",
        "message": "MenuApplication with id App=BNAHABIL,Menu=ADMIN_APPLICATION already exists",
        "timestamp": "2025-11-18T09:44:46.9350237"
      },
      status: 409,
      statusText: 'Conflict',
      message: 'HTTP Error'
    } as HttpErrorResponse;

    this.sharedService.handleError(mockModernError, 'Test Modern Simple Error');
  }

  /**
   * Test Legacy ResponseSAGA Simple Error
   */
  testLegacySimpleError() {
    console.log('🧪 Testing Legacy Simple Error...');
    
    const mockLegacySimpleError = {
      error: {
        "returnCode": 1,
        "message": "Validation failed: Invalid data format"
      },
      status: 400,
      statusText: 'Bad Request',
      message: 'HTTP Error'
    } as HttpErrorResponse;

    this.sharedService.handleError(mockLegacySimpleError, 'Test Legacy Simple Error');
  }

  /**
   * Test Partial Success Batch Operation
   */
  testPartialSuccessBatch() {
    console.log('🧪 Testing Partial Success Batch...');
    
    const mockPartialSuccess = {
      error: {
        "returnCode": 1,
        "message": "Batch operation completed with errors: 2 successful, 1 failed",
        "data": {
          "successful": [
            { "index": 0, "identifier": "Item1" },
            { "index": 1, "identifier": "Item2" }
          ],
          "failed": [
            {
              "index": 2,
              "identifier": "Item3",
              "error": "Validation failed for Item3"
            }
          ],
          "mode": "BEST_EFFORT",
          "partialSuccess": true
        }
      },
      status: 207,
      statusText: 'Multi-Status',
      message: 'HTTP Error'
    } as HttpErrorResponse;

    this.sharedService.handleError(mockPartialSuccess, 'Test Partial Success');
  }

  /**
   * Test Modern Batch Operation Error
   */
  testModernBatchError() {
    console.log('🧪 Testing Modern Batch Error...');
    
    const mockModernBatchError = {
      error: {
        "code": "BATCH_OPERATION_FAILED",
        "message": "Batch operation failed: 2 errors occurred",
        "errors": [
          {
            "index": 0,
            "identifier": "User123",
            "error": "User already exists"
          },
          {
            "index": 1,
            "identifier": "User456", 
            "error": "Invalid email format"
          }
        ],
        "timestamp": "2025-11-18T10:00:00.000Z",
        "totalItems": 3
      },
      status: 207,
      statusText: 'Multi-Status',
      message: 'HTTP Error'
    } as HttpErrorResponse;

    this.sharedService.handleError(mockModernBatchError, 'Test Modern Batch Error');
  }

  /**
   * Test Enhanced Success Messages
   */
  testEnhancedSuccess() {
    console.log('🧪 Testing Enhanced Success...');
    
    this.sharedService.showEnhancedSuccess(
      'Operation completed successfully with advanced features',
      '✅ Enhanced Success',
      {
        timestamp: new Date().toISOString(),
        operationType: 'test-operation',
        systemType: 'modern'
      }
    );
  }

  /**
   * Test Batch Summary
   */
  testBatchSummary() {
    console.log('🧪 Testing Batch Summary...');
    
    this.sharedService.showBatchSummary({
      total: 10,
      successful: 7,
      failed: 3,
      errors: [
        { index: 2, identifier: 'Item2', error: 'Duplicate entry' },
        { index: 5, identifier: 'Item5', error: 'Invalid format' },
        { index: 8, identifier: 'Item8', error: 'Permission denied' }
      ]
    });
  }

  /**
   * Test System Detection
   */
  testSystemDetection() {
    console.log('🧪 Testing System Detection...');
    
    setTimeout(() => {
      this.sharedService.showSystemDetection('modern', 'Global Exception Handler detected');
    }, 500);
    
    setTimeout(() => {
      this.sharedService.showSystemDetection('legacy', 'ResponseSAGA system detected');
    }, 2000);
  }

  /**
   * Run all tests in sequence
   */
  runAllTests() {
    console.group('🧪 Starting Toast Error Handling Tests');
    
    this.testEnhancedSuccess();
    
    setTimeout(() => this.testSystemDetection(), 1000);
    setTimeout(() => this.testModernSimpleError(), 3500);
    setTimeout(() => this.testLegacySimpleError(), 6000);
    setTimeout(() => this.testLegacyBatchError(), 8500);
    setTimeout(() => this.testModernBatchError(), 12000);
    setTimeout(() => this.testPartialSuccessBatch(), 15500);
    setTimeout(() => this.testBatchSummary(), 19000);
    
    setTimeout(() => {
      console.groupEnd();
      console.log('✅ All toast tests completed!');
    }, 22000);
  }
}

// You can add this to any component to test the toast system:
// 
// constructor(private toastTest: ToastTestService) {}
// 
// testToasts() {
//   this.toastTest.runAllTests();
// }
