/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('CoworkersController', CoworkersController);

  CoworkersController.$inject = ['$log'];

  /* @ngInject */
  function CoworkersController($log) {
    var vm = this;
    vm.title = 'Coworkers panel';

  }

})(angular);
