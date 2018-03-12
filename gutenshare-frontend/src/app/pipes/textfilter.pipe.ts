import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'textFilter'
})

export class TextfilterPipe implements PipeTransform {
  transform(items: any[], prop: string, searchText: string): any[] {
    if(!items) return [];
    if(!searchText) return items;

    console.log(items);
    console.log(prop);
    console.log(searchText);

    searchText = searchText.toLowerCase();

    if (prop==null) {
      return items.filter( it => {
        return it.toLowerCase().includes(searchText);
      });

    }

    return items.filter( it => {
      return it[prop].toLowerCase().includes(searchText);
    });
  }
}
