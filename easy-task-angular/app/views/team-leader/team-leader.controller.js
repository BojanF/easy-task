/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('TeamLeaderController', TeamLeaderController);

  TeamLeaderController.$inject = ['$log'];

  /* @ngInject */
  function TeamLeaderController($log) {
    var vm = this;
    vm.title = 'Teams that you are leading';

  }

})(angular);
