/**
 * Created by Bojan on 8/24/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .config(registerState);


  registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('task-details', {
      url: '/task-details/:taskId',
      templateUrl: 'app/views/task-details/task-details.view.html',
      controller: 'TaskDetailsController',
      controllerAs: 'vm'
    });
  }

})(angular);
