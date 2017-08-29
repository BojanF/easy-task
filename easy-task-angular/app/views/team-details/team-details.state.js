/**
 * Created by Bojan on 8/28/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .config(registerState);


  registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('team-details', {
      url: '/team-details/:teamId',
      templateUrl: 'app/views/team-details/team-details.view.html',
      controller: 'TeamDetailsController',
      controllerAs: 'vm'
    });
  }

})(angular);
