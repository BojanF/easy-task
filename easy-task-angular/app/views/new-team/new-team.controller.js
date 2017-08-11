/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('NewTeamController', NewTeamController);

  NewTeamController.$inject = ['$log'];

  /* @ngInject */
  function NewTeamController($log) {
    var vm = this;
    vm.title = 'Create new project';

  }

})(angular);
