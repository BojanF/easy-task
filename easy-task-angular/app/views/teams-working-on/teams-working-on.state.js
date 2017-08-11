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

    $stateProvider.state('teams-working-on', {
      url: '/teams-working-on',
      templateUrl: 'app/views/teams-working-on/teams-working-on.view.html',
      controller: 'TeamsWorkingOnController',
      controllerAs: 'vm'
    });
  }

})(angular);

