/**
 * Created by Marijo on 02-Sep-17.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .config(registerState);


  registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('login', {
      url: '/login',
      templateUrl: 'app/views/login/login.view.html',
      controller: 'LoginController',
      controllerAs: 'vm'
    });
  }

})(angular);
