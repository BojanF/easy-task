/**
 * Created by Marijo on 10-Sep-17.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .config(registerState);


  registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('profile', {
      url: '/profile',
      templateUrl: 'app/views/profile/profile.view.html',
      controller: 'ProfileController',
      controllerAs: 'vm'
    });

  }

})(angular);
