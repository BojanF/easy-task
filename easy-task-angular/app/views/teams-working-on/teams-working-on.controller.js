/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('TeamsWorkingOnController', TeamsWorkingOnController);

  TeamsWorkingOnController.$inject = ['$log'];

  /* @ngInject */
  function TeamsWorkingOnController($log) {
    var vm = this;
    vm.title = ' Teams that you are member of';

  }

})(angular);
