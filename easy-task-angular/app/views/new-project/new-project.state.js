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

    $stateProvider.state('new-project', {
      url: '/new-project',
      templateUrl: 'app/views/new-project/new-project.view.html',
      controller: 'NewProjectsController',
      controllerAs: 'vm'
    });
  }

})(angular);
