export const SCHOOLS: any[] = [
  {
    'name': 'ZHAW',
    'departments': [
      {
        'name': 'School of engineering',
        'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
      },
      {
        'name': 'School of economics',
        'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
      },
      {
        'name': 'School of law',
        'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
      }
    ]
  },
  {
    'name': 'ethz',
    'departments': [
      {
        'name': 'Physics',
        'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
      },
      {
        'name': 'Chemistry',
        'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
      },
      {
        'name': 'Biology',
        'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
      }
    ]
  },
  {
    'name': 'UZH',
    'departments': [
      {
        'name': 'DüSe',
        'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
      },
      {
        'name': 'Linguistik',
        'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
      },
      {
        'name': 'Mathematik',
        'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
      }
    ]
  },
];

export const DOCUMENTS: any[] = [
  {
    "id": "63f169e5-422c-11e8-b1e0-daa5c0acf944",
    "title": "PSIT Summary",
    "documentType": "Summary",
    "school": "ZHAW",
    "department": "School of engineering",
    "course": "psit3",
    "fileType": "docx",
    "tags": [],
    "description": "desc",
    "rating": 4,
    "comments": [
      "TEST",
      "TEST2"],
    "user": "Alicia"
  },
  {
    "id": "782a9176-422c-11e8-b1e0-daa5c0acf944",
    "title": "Communication diagram",
    "documentType": "Exercice",
    "school": "ZHAW",
    "department": "School of engineering",
    "course": "psit3",
    "fileType": "pdf",
    "tags": [],
    "description": "xml",
    "rating": 3,
    "comments": [],
    "user": "Kaspar"
  },
  {
    "id": "4b4f5f2a-4516-11e8-97c5-0265f087e1ac",
    "title": "Trial exam",
    "documentType": "Test",
    "school": "ZHAW",
    "department": "School of engineering",
    "course": "psit3",
    "fileType": "pdf",
    "tags": [],
    "description": "desc",
    "rating": 5,
    "comments": [],
    "user": "Arik"
  },
  {
    "id": "65e7b0db-4516-11e8-97c5-0265f087e1ac",
    "title": "Manual for Gutenshare",
    "documentType": "Transcript",
    "school": "ZHAW",
    "department": "School of engineering",
    "course": "psit3",
    "fileType": "pdf",
    "tags": [
      "TEST",
      "TEST2"
    ],
    "description": "desc",
    "numberOfDownloads": 505,
    "rating": 5,
    "comments": [
      {
        "title": "this is the first title",
        "user": "user1",
        "content": "Das ist der Kommentar, woowwiiiiiieee"
      },
      {
        "title": "this is the second title",
        "user": "user2",
        "content": "Das ist der zweite Kommentar, woowwiiiiiieee"}],
    "user": "Louis"
  }
];

export const SPECIFICDOCUMENT: any =
  {
    "id": "63f169e5-422c-11e8-b1e0-daa5c0acf944",
    "title": "Manual for Gutenshare",
    "documentType": "PDF",
    "school": "ZHAW",
    "department": "School of engineering",
    "course": "psit3",
    "fileType": "pdf",
    "downloadLink": "wasad132a35d44f2adfadsg",
    "uploadDate": "20.04.2018",
    "tags": [
      "tag1",
      "tag2",
      "tag3"
    ],
    "description": "desc",
    "numberOfDownloads": 3,
    "rating": 4,
    "comments": [
      {
        "user": "user1",
        "content": "Das ist der Kommentar, woowwiiiiiieee"
      },
      {
        "user": "user2",
        "content": "Das ist der zweite Kommentar, woowwiiiiiieee"}],
    "user": "Alicia"
  };

export const DOCUMENT_TYPES: string[] = ['Test', 'Summary', 'Book', 'Transcript', 'Exercise', 'Exam'];
