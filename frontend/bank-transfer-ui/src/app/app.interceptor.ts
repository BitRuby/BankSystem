import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {finalize, tap} from 'rxjs/operators';
import {NgxSpinnerService} from 'ngx-spinner';


@Injectable()
export class AppInterceptor implements HttpInterceptor {
  private count = 0;
  constructor(private spinner: NgxSpinnerService) {
  }
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req)
      .pipe(
        tap(event => {
          if (event instanceof HttpResponse) {
            this.count++;
            if (this.count === 1) {
              this.spinner.show();
            }
          }
        }, error => {
          console.error(error.error);
        }),
        finalize(() => {
          this.count--;
          if (this.count === 0) {
            setTimeout(() => {
              this.spinner.hide();
            }, 1000);
          }
        })
      );

  }
}
