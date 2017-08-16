/**
 * Created by Bojan on 11/14/2016.
 */

(function () {
  'use strict';

  angular
    .module('easy-task-angular')
    .component('singleSelect', {
      templateUrl: 'app/components/single-select/single-select-component.view.html',
      bindings: {
        model: "=",
        entities: "<",
        required: "@", //if you don`t want to be required, don`t pass any values, just skip this attr
        placeholder: "@",
        info: "@"
      }

    });

})();


