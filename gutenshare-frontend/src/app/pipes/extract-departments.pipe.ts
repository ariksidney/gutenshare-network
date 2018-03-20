import { Pipe, PipeTransform } from '@angular/core';
import {forEach} from "@angular/router/src/utils/collection";

@Pipe({
  name: 'extractDepartments'
})

export class ExtractDepartmentPipe implements PipeTransform {

  transform(schools: any[]): string[] {

    if(!schools) return [];

    let departments: string[] = new Array();

    schools.forEach(school => {
      school.departments.forEach(department => {
        departments.push(department.name);
      });
    });

    return departments;
  }

}
