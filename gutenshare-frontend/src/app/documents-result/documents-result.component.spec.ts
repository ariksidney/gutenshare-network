import {async, ComponentFixture, TestBed} from "@angular/core/testing";
import {DocumentsResultComponent} from "./documents-result.component";
import {RouterTestingModule} from "@angular/router/testing";

describe('Component: DocumentsResultComponent', () => {
  let documentBrowserComponent: DocumentsResultComponent;
  let fixture: ComponentFixture<DocumentsResultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [DocumentsResultComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentsResultComponent);

    documentBrowserComponent = fixture.debugElement.componentInstance;
  });

  it('should change the current search criteria after sorting', () => {
    documentBrowserComponent.documents = [];

    documentBrowserComponent.changeSortingCriteria("title");

    expect(documentBrowserComponent.currentSearchCriteria).toEqual("title");
  });

  it('should toggle sort reverse after each call of sorting criteria', () => {
    documentBrowserComponent.documents = [];

    documentBrowserComponent.changeSortingCriteria("title");
    documentBrowserComponent.changeSortingCriteria("title");

    expect(documentBrowserComponent.sortReverse).toBeTruthy();
  });

  it('should not toggle sort reverse after calling sort criteria on different column', () => {
    documentBrowserComponent.documents = [];

    documentBrowserComponent.changeSortingCriteria("title");
    documentBrowserComponent.changeSortingCriteria("id");

    expect(documentBrowserComponent.sortReverse).toBeFalsy();
  });
});

