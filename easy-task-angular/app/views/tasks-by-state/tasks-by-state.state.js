/**
 * Created by Bojan on 8/10/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .config(registerState);


  registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('tasks-by-state', {
      url: '/tasks-by-state',
      templateUrl: 'app/views/tasks-by-state/tasks-by-state.view.html',
      controller: 'TasksByStateController',
      controllerAs: 'vm'
    });
  }

})(angular);
