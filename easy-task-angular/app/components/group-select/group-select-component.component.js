/**
 * Created by Bojan on 11/14/2016.
 */

(function () {
  'use strict';

  angular
    .module('easy-task-angular')
    .component('groupSelect', {
      templateUrl: 'app/components/group-select/group-select-component.view.html',
      bindings: {
        gsModel: "=",
        groups: "<"
      }

    });

})();


