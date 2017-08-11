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

    $stateProvider.state('team-leader', {
      url: '/team-leader',
      templateUrl: 'app/views/team-leader/team-leader.view.html',
      controller: 'TeamLeaderController',
      controllerAs: 'vm'
    });
  }

})(angular);

