/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('NewProjectsController', NewProjectsController);

  NewProjectsController.$inject = ['$log'];

  /* @ngInject */
  function NewProjectsController($log) {
    var vm = this;
    vm.title = 'Create new project';

  }

})(angular);
