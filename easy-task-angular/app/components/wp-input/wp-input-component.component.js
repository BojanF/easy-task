/**
 * Created by Bojan on 10/31/2016.
 */
(function () {
  'use strict';

  angular
    .module('easy-task-angular')
    .component('wpInput', {
      templateUrl: 'app/components/wp-input/wp-input-component.view.html',
      bindings: {
        wpLabel: '@',
        wpType: '@',
        wpModel: '=',
        wpRequired: '@',
        wpFocus: "@"
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

  //funk.$inject = [];


  //console.log('test1234');
  /* @ngInject */
  /*function funk() {

   console.log('test');
   var component = {
   templateUrl: 'app\components\wp-input\wp-input-component.view.html',
   bindings: {
   wpLabel: '@',
   wpType: '@',
   wpModel: '='
   },
   controller: InputComponent,
   controllerAs: 'vm'

   };
   return component;
   }*/

  InputComponent.$inject = ['$attrs'];
  function InputComponent($attrs){
    this.wpLabel = $attrs.wpLabel;
    this.wpType = $attrs.wpType;
    //this.wpModel = $attrs.wpModel;
    this.wpRequired = $attrs.wpRequired;
    this.wpFocus = $attrs.wpFocus;

  };



})();

