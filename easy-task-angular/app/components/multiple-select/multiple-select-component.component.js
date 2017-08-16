/**
 * Created by Bojan on 8/13/2017.
 */

(function () {
  'use strict';

  angular
    .module('easy-task-angular')
    .component('multipleSelect', {
      templateUrl: 'app/components/multiple-select/multiple-select-component.view.html',
      bindings: {
        // placeholder: '@',
        // label: '@',
        // type: '@',
        users: '<',
        model: '=',
        required: '@',
        info: '@'
      },


    });







})();

