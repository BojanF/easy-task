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

    $stateProvider.state('administrating-projects', {
      url: '/administrating-projects',
      templateUrl: 'app/views/administrating-projects/administrating-projects.view.html',
      controller: 'AdministratingProjectsController',
      controllerAs: 'vm'
    });
  }

})(angular);
