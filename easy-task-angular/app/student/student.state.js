/**
 * Created by Bojan on 10/17/2016.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .config(registerState);


  //registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('student', {
      url: '/students',
      templateUrl: 'app/student/student.view.html',
      controller: 'StudentController',
      controllerAs: 'vm'
    });
  }

})(angular);

