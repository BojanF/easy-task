/**
 * Created by Bojan on 10/31/2016.
 */
(function () {
  'use strict';

  angular
    .module('easy-task-angular')
    .component('inputComponent', {
      templateUrl: 'app/components/input-component/input-component.view.html',
      bindings: {
        placeholder: '@',
        label: '@',
        type: '@',
        model: '=',
        required: '@',
        focus: "@"
      },
      controller: InputComponent

    }).directive('focusMe', function () {
        return {
          restrict: 'A',
          scope: {
            focusMe: '='
          },
          link: function (scope, element) {
            scope.$watch('focusMe', function (val) {
              if (val) {
                element[0].focus();
              }
            });
          }
        };
  });



  InputComponent.$inject = ['$attrs'];
  function InputComponent($attrs){
    this.placeholder = $attrs.placeholder;
    this.label = $attrs.label;
    this.type = $attrs.type;
    //this.wpModel = $attrs.wpModel;
    this.required = $attrs.required;
    this.focus = $attrs.focus;

  };



})();

