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

    $stateProvider.state('coworkers', {
      url: '/coworkers',
      templateUrl: 'app/views/coworkers/coworkers.view.html',
      controller: 'CoworkersController',
      controllerAs: 'vm'
    });
  }

})(angular);

