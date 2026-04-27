import { TestBed } from '@angular/core/testing';
import { MessageService } from 'primeng/api';
import { OperationNotificationService, OperationType } from '../operation-notification.service';
import { NotificationService } from '../notification.service';
import { BatchAssignmentResult } from '../../../models/batch-assignement-result';

describe('OperationNotificationService', () => {
  let service: OperationNotificationService;
  let messageService: jasmine.SpyObj<MessageService>;
  let notificationService: jasmine.SpyObj<NotificationService>;

  beforeEach(() => {
    const messageServiceSpy = jasmine.createSpyObj('MessageService', ['add', 'clear']);
    const notificationServiceSpy = jasmine.createSpyObj('NotificationService', [
      'addNotification',
      'addAlert'
    ]);

    TestBed.configureTestingModule({
      providers: [
        OperationNotificationService,
        { provide: MessageService, useValue: messageServiceSpy },
        { provide: NotificationService, useValue: notificationServiceSpy }
      ]
    });

    service = TestBed.inject(OperationNotificationService);
    messageService = TestBed.inject(MessageService) as jasmine.SpyObj<MessageService>;
    notificationService = TestBed.inject(NotificationService) as jasmine.SpyObj<NotificationService>;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('handleBatchOperationResult', () => {
    it('should handle complete success', () => {
      const result: BatchAssignmentResult = {
        successful: ['PROF1', 'PROF2'],
        failed: {},
        totalProcessed: 2,
        successCount: 2,
        failureCount: 0
      };

      service.handleBatchOperationResult(
        result,
        OperationType.PROFILE_ASSIGNMENT,
        { targetUser: 'John Doe' }
      );

      expect(messageService.add).toHaveBeenCalledWith(
        jasmine.objectContaining({ severity: 'success' })
      );
    });

    it('should handle partial success', () => {
      const result: BatchAssignmentResult = {
        successful: ['PROF1'],
        failed: { 'PROF2': 'Error message' },
        totalProcessed: 2,
        successCount: 1,
        failureCount: 1
      };

      service.handleBatchOperationResult(
        result,
        OperationType.PROFILE_ASSIGNMENT,
        { targetUser: 'John Doe' }
      );

      expect(messageService.add).toHaveBeenCalledWith(
        jasmine.objectContaining({ severity: 'warn' })
      );
    });

    it('should handle complete failure', () => {
      const result: BatchAssignmentResult = {
        successful: [],
        failed: { 'PROF1': 'Error 1', 'PROF2': 'Error 2' },
        totalProcessed: 2,
        successCount: 0,
        failureCount: 2
      };

      service.handleBatchOperationResult(
        result,
        OperationType.PROFILE_ASSIGNMENT,
        { targetUser: 'John Doe' }
      );

      expect(messageService.add).toHaveBeenCalledWith(
        jasmine.objectContaining({ severity: 'error' })
      );
    });
  });

  describe('handleSingleSuccess', () => {
    it('should show toast and add notification', () => {
      service.handleSingleSuccess(
        'Operation completed',
        OperationType.USER_CREATION,
        { targetUser: 'Jane Doe' }
      );

      expect(messageService.add).toHaveBeenCalled();
      expect(notificationService.addNotification).toHaveBeenCalled();
    });
  });

  describe('handleSingleError', () => {
    it('should show toast and add alert for string error', () => {
      service.handleSingleError(
        'Operation failed',
        OperationType.USER_DELETION,
        { targetUser: 'Jane Doe' }
      );

      expect(messageService.add).toHaveBeenCalled();
      expect(notificationService.addAlert).toHaveBeenCalled();
    });

    it('should show toast and add alert for Error object', () => {
      const error = new Error('Something went wrong');
      
      service.handleSingleError(
        error,
        OperationType.USER_UPDATE,
        { targetUser: 'John Doe' }
      );

      expect(messageService.add).toHaveBeenCalled();
      expect(notificationService.addAlert).toHaveBeenCalled();
    });
  });
});
