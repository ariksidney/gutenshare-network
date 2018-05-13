import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class TransferService {
  // Observable string sources
  private username = new Subject<any>();
  // Observable string streams
  userEmitted$ = this.username.asObservable();
  // Service message commands
  emitUsername(change: any) {
    this.username.next(change);
  }
}
