/**
 * Created by Bojan on 8/7/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('StatsChartsController', StatsChartsController);

  StatsChartsController.$inject = ['$log'];

  /* @ngInject */
  function StatsChartsController($log) {
    var vm = this;
    vm.title = 'stats-charts';

    vm.c3DataTest = {
      donuts: {
        points: {
          tasks:[
            {"Not started": 25},
            {"In progress": 20},
            {"Finish": 80},
            {"Deadline braech": 3}
          ],
          projects:[
            {"Created": 25},
            {"Not started": 85},
            {"In progress": 80},
            {"Up to date": 35},
            {"Finished": 45},
            {"Deadline braech": 0}
          ]
        },
        columns: {
          tasks:[
            {"id": "Not started", "type": "donut"},
            {"id": "In progress", "type": "donut"},
            {"id": "Finish", "type": "donut"},
            {"id": "Deadline braech", "type": "donut"}
          ],
          projects:[
            {"id": "Created", "type": "donut"},
            {"id": "Not started", "type": "donut"},
            {"id": "In progress", "type": "donut"},
            {"id": "Up to date", "type": "donut"},
            {"id": "Finished", "type": "donut"},
            {"id": "Deadline braech", "type": "donut"}
          ]
        }
      },
      time_chart:{
        points: [
          {/*"x": "2017-01-01",*/ "Created": 30, "Not started": 0, "In progress": 0, "Up to date": 0, "Finished": 0, "Deadline braech": 0},
          {/*"x": "2017-01-02",*/ "Created": 5, "Not started": 10, "In progress": 10, "Up to date": 5, "Finished": 5, "Deadline braech": 0},
          {/*"x": "2017-01-03",*/ "Created": 3, "Not started": 20, "In progress": 45, "Up to date": 30, "Finished": 35, "Deadline braech": 1},
          {/*"x": "2017-01-04",*/ "Created": 10, "Not started": 15, "In progress": 70, "Up to date": 45, "Finished": 38, "Deadline braech": 0},
          {/*"x": "2017-01-05",*/ "Created": 18, "Not started": 25, "In progress": 60, "Up to date": 27, "Finished": 40, "Deadline braech": 2},
          {/*"x": "2017-01-06",*/ "Created": 25, "Not started": 20, "In progress": 85, "Up to date": 35, "Finished": 45, "Deadline braech": 0},
          {/*"x": "2017-01-06",*/ "Created": 25, "Not started": 20, "In progress": 85, "Up to date": 35, "Finished": 45, "Deadline braech": 0}
        ],
        columns: [
           // {"id": "x", "type": ""},
          {"id": "Created", "type": "spline"},
          {"id": "Not started", "type": "spline"},
          {"id": "In progress", "type": "spline"},
          {"id": "Up to date", "type": "spline"},
          {"id": "Finished", "type": "spline"},
          {"id": "Deadline braech", "type": "spline"}
        ],
        tickValues: {"Created": "2017-01-01",
                      "Not started": "2017-01-02",
                      "In progress": "2017-01-03", "Up to date": "2017-01-04", "Finished": "2017-01-05", "Deadline braech": "2017-01-06"},

        tickValues2: [ "2017-01-01",
                       "2017-01-02",
                      "2017-01-03", "2017-01-04", "2017-01-05",  "2017-01-06"],
        proba: {"id": "x", "name": "My Data points"}
      }
    }
  }

})(angular);
