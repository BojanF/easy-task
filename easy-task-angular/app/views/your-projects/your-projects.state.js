/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .config(registerState);


  registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('your-projects', {
      url: '/your-projects',
      templateUrl: 'app/views/your-projects/your-projects.view.html',
      controller: 'YourProjectsController',
      controllerAs: 'vm'
    });
  }

})(angular);
