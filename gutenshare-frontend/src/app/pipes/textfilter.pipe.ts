import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'textFilter'
})

export class TextfilterPipe implements PipeTransform {
  transform(items: any[], searchText: string): any[] {
    if(!items) return [];
    if(!searchText) return items;

    console.log(items);
    console.log(searchText);

    searchText = searchText.toLowerCase();


    return items.filter( it => {
      return it.name.toLowerCase().includes(searchText);
    });
  }
}
