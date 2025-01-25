import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByRole',
})
export class FilterByRolePipe implements PipeTransform {
  transform(employers: any[], role: string): any[] {
    if (!employers || !role) {
      return employers;
    }
    return employers.filter((employer) => employer.role === role);
  }
}

