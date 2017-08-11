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

    $stateProvider.state('new-team', {
      url: '/new-team',
      templateUrl: 'app/views/new-team/new-team.view.html',
      controller: 'NewTeamController',
      controllerAs: 'vm'
    });
  }

})(angular);

