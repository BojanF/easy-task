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

    $stateProvider.state('project-details', {
      url: '/project-details/:projectID',
      templateUrl: 'app/views/project-details/project-details.view.html',
      controller: 'ProjectDetailsController',
      controllerAs: 'vm'
    });

  }

})(angular);
