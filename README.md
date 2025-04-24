{
  // log to console
  "Print to console": {
    "prefix": "cl",
    "scope": "javascript,typescript,javascriptreact",
    "body": ["console.log($1)"],
    "description": "console.log"
  },

  // ─────────────────────────────────────────────────────
  // COMPONENT + LIFECYCLE
  // ─────────────────────────────────────────────────────

  "Angular Component": {
    "prefix": "ng-comp",
    "scope": "typescript",
    "body": [
      "import { Component, OnInit } from '@angular/core';",
      "",
      "@Component({",
      "  selector: 'app-${1:feature}',",
      "  templateUrl: './${1:feature}.component.html',",
      "  styleUrls: ['./${1:feature}.component.scss']",
      "})",
      "export class ${2:${1/(?:^|-)([a-z])/ ${1:/upcase}/g}}Component implements OnInit {",
      "  constructor() { }",
      "",
      "  ngOnInit(): void {",
      "    $0",
      "  }",
      "}"
    ],
    "description": "Generate a new Angular component class (with OnInit)"
  },

  "OnInit Hook": {
    "prefix": "nginit",
    "scope": "typescript",
    "body": [
      "ngOnInit(): void {",
      "  $0",
      "}"
    ],
    "description": "Angular OnInit lifecycle hook"
  },

  "OnChanges Hook": {
    "prefix": "ngchanges",
    "scope": "typescript",
    "body": [
      "ngOnChanges(changes: SimpleChanges): void {",
      "  $0",
      "}"
    ],
    "description": "Angular OnChanges lifecycle hook"
  },

  "OnDestroy Hook": {
    "prefix": "ngdestroy",
    "scope": "typescript",
    "body": [
      "ngOnDestroy(): void {",
      "  $0",
      "}"
    ],
    "description": "Angular OnDestroy lifecycle hook"
  },

  // ─────────────────────────────────────────────────────
  // DIRECTIVES
  // ─────────────────────────────────────────────────────

  "NgIf": {
    "prefix": "ngif",
    "scope": "html",
    "body": ["*ngIf=\"${1:condition}\""],
    "description": "Angular *ngIf structural directive"
  },

  "NgFor": {
    "prefix": "ngfor",
    "scope": "html",
    "body": ["*ngFor=\"let ${1:item} of ${2:items}; let ${3:i}=index\""],
    "description": "Angular *ngFor structural directive"
  },

  // ─────────────────────────────────────────────────────
  // INPUT / OUTPUT / SERVICES
  // ─────────────────────────────────────────────────────

  "Input Decorator": {
    "prefix": "nginput",
    "scope": "typescript",
    "body": ["@Input() ${1:propName}: ${2:type};"],
    "description": "Angular @Input() property"
  },

  "Output Decorator": {
    "prefix": "ngoutput",
    "scope": "typescript",
    "body": ["@Output() ${1:eventName} = new EventEmitter<${2:type}>();"],
    "description": "Angular @Output() EventEmitter"
  },

  "Inject Service": {
    "prefix": "nginject",
    "scope": "typescript",
    "body": ["constructor(private ${1:myService}: ${2:MyService}) { }"],
    "description": "Inject an Angular service in constructor"
  },

  // ─────────────────────────────────────────────────────
  // HTTP + OBSERVABLES
  // ─────────────────────────────────────────────────────

  "Import HttpClient": {
    "prefix": "nghttpimport",
    "scope": "typescript",
    "body": ["import { HttpClient } from '@angular/common/http';"],
    "description": "Import Angular HttpClient"
  },

  "HTTP GET": {
    "prefix": "nghttpget",
    "scope": "typescript",
    "body": [
      "this.http.get<${1:ResponseType}>('${2:url}').subscribe((${3:res}) => {",
      "  $0",
      "});"
    ],
    "description": "Angular HttpClient GET + subscribe"
  },

  "Observable Subscribe": {
    "prefix": "ngsub",
    "scope": "typescript",
    "body": [
      "${1:observable}.subscribe((${2:data}) => {",
      "  $0",
      "});"
    ],
    "description": "Subscribe to an Observable"
  },

  // ─────────────────────────────────────────────────────
  // ROUTER
  // ─────────────────────────────────────────────────────

  "Router Navigate": {
    "prefix": "ngnav",
    "scope": "typescript",
    "body": ["this.router.navigate(['/${1:path}']);"],
    "description": "Angular Router navigate()"
  }
}
