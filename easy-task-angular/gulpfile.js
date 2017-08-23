/**
 *
 * Developed as a part of a project founded by Sorsix
 *
 * @Authors
 *  Tomce Delev
 *  Dragan Sahpaski
 *  Riste Stojanov
 *
 **/
var gulp = require('gulp');
var concat = require('gulp-concat');
var templateCache = require('gulp-angular-templatecache');
var rev = require('gulp-rev-append');
var eslint = require('gulp-eslint');
var connect = require('gulp-connect');
var fs = require("fs");

var JS_APP = [
  'app/app.js',

  //home-page
  'app/views/home-page/home-page.state.js',
  'app/views/home-page/home-page.controller.js',
  'app/views/home-page/home-page.service.js',

  //stats-charts
  'app/views/stats-charts/stats-charts.state.js',
  'app/views/stats-charts/stats-charts.controller.js',
  'app/views/stats-charts/stats-charts.service.js',

  //project-details
  'app/views/project-details/project-details.state.js',
  'app/views/project-details/project-details.controller.js',
  'app/views/project-details/project-details.service.js',

  //administrating-projects
  'app/views/administrating-projects/administrating-projects.state.js',
  'app/views/administrating-projects/administrating-projects.controller.js',
  'app/views/administrating-projects/administrating-projects.service.js',

  //your-projects
  'app/views/your-projects/your-projects.state.js',
  'app/views/your-projects/your-projects.controller.js',
  'app/views/your-projects/your-projects.service.js',

  //new-project
  'app/views/new-project/new-project.state.js',
  'app/views/new-project/new-project.controller.js',
  'app/views/new-project/new-project.service.js',

  //team-leader
  'app/views/team-leader/team-leader.state.js',
  'app/views/team-leader/team-leader.controller.js',
  'app/views/team-leader/team-leader.service.js',

  //teams-working-on
  'app/views/teams-working-on/teams-working-on.state.js',
  'app/views/teams-working-on/teams-working-on.controller.js',

  //coworkers
  'app/views/coworkers/coworkers.state.js',
  'app/views/coworkers/coworkers.controller.js',

  //new-team
  'app/views/new-team/new-team.state.js',
  'app/views/new-team/new-team.controller.js',
  'app/views/new-team/new-team.service.js',

  //tasks-by-state
  'app/views/tasks-by-state/tasks-by-state.state.js',
  'app/views/tasks-by-state/tasks-by-state.controller.js',
  'app/views/tasks-by-state/tasks-by-state.service.js',

  //components
  'app/components/single-select/single-select-component.component.js',
  'app/components/input-component/input-component.component.js',
  'app/components/multiple-select/multiple-select-component.component.js'
];

var TEMPLATES_SRC = [
  'app/views/home-page/home-page.view.html',
  'app/views/stats-charts/stats-charts.view.html',
  'app/views/project-details/project-details.view.html',
  'app/views/administrating-projects/administrating-projects.view.html',
  'app/views/your-projects/your-projects.view.html',
  'app/views/new-project/new-project.view.html',
  'app/views/team-leader/team-leader.view.html',
  'app/views/teams-working-on/teams-working-on.view.html',
  'app/views/coworkers/coworkers.view.html',
  'app/views/new-team/new-team.view.html',
  'app/views/tasks-by-state/tasks-by-state.view.html'
];

var CSS_APP = [
  'css/main.css'
];

var FONTS_LIB = [
  'bower_components/components-font-awesome/fonts/fontawesome-webfont.woff2',
  'bower_components/components-font-awesome/fonts/fontawesome-webfont.woff',
  'bower_components/components-font-awesome/fonts/fontawesome-webfont.ttf'
];

var CSS_LIB = [
  'bower_components/bootstrap/dist/css/bootstrap.css',
  'bower_components/components-font-awesome/css/font-awesome.min.css',
  'bower_components/angular-ui-select/dist/select.css'];


var JS_LIB = [
  'bower_components/jquery/dist/jquery.min.js',
  'bower_components/bootstrap/dist/js/bootstrap.min.js',
  'bower_components/angular/angular.js',
  'bower_components/momentjs/moment.js',
  'bower_components/angular-ui-router/release/angular-ui-router.js',
  'bower_components/angular-ui-select/dist/select.js',
  'bower-components/angular-smart-table/dist/smart-table.js',
  'bower_components/angular-resource/angular-resource.js',
  'bower_components/d3/d3.min.js',
  'bower_components/c3/c3.min.js',
  'bower_components/c3-angular/c3-angular.min.js'
];


/**
 *   The location of the resources for deploy
 */
var DESTINATION = 'dest/';

var FONTS_DESTINATION = 'fonts/';
/**
 * The single page initial html file. It will be altered
 * by this script.
 */
var INDEX_FILE = 'index.html';
/**
 * The name of the angular module
 */
var MODULE_NAME = 'easy-task-angular';
/**
 * The URL of the back-end API
 */
//staro pred lab6
//var API_URL = 'http://localhost:8080/servlet-showcase/api';
//novo za lab6
var API_URL = 'http://localhost:8080/api';
/**
 * Route to which the API calls will be mapped
 */
var API_ROUTE = '/api';

/**
 * Task for concatenation of the js libraries used
 * in this project
 */
gulp.task('concat_js_lib', function () {
  return gulp.src(JS_LIB) // which js files
    .pipe(concat('lib.js')) // concatenate them in lib.js
    .pipe(gulp.dest(DESTINATION)); // save lib.js in the DESTINATION folder
});

/**
 * Task for concatenation of the css libraries used
 * in this project
 */
gulp.task('concat_css_lib', function () {
  return gulp.src(CSS_LIB) // which css files
    .pipe(concat('lib.css')) // concat them in lib.css
    .pipe(gulp.dest(DESTINATION)); // save lib.css in the DESTINATION folder
});

/**
 * Task for concatenation of the js code defined
 * in this project
 */
gulp.task('concat_js_app', function () {
  return gulp.src(JS_APP)
    .pipe(concat('src.js'))
    .pipe(gulp.dest(DESTINATION))
});

/**
 * Task for concatenation of the css code defined
 * in this project
 */
gulp.task('concat_css_app', function () {
  return gulp.src(CSS_APP)
    .pipe(concat('app.css'))
    .pipe(gulp.dest(DESTINATION))
});

/**
 * Task for concatenation of the html templates defined
 * in this project
 */
gulp.task('templates', function () {
  return gulp.src(TEMPLATES_SRC) // which html files
    .pipe(
      templateCache('templates.js', { // compile them as angular templates
        module: MODULE_NAME,        // from module MODULE_NAME
        root: 'app/views'                // of the app
      }))
    .pipe(gulp.dest(DESTINATION));
});

/**
 * Task for adding the revision as parameter
 * for cache braking
 */
gulp.task('cache-break', function () {
  return gulp.src(INDEX_FILE) // use the INDEX_FILE as source
    .pipe(rev())            // append the revision to all resources
    .pipe(gulp.dest('.'));  // save the modified file at the same destination
});

gulp.task('fonts', function () {
  return gulp.src(FONTS_LIB)
    .pipe(gulp.dest(FONTS_DESTINATION))
});

var tasks = [
  'concat_js_lib',
  'concat_css_lib',
  'concat_js_app',
  'concat_css_app',
  'fonts',
  'templates'
];

gulp.task('build', tasks, function () {
  gulp.start('cache-break');
});

gulp.task('watch', function () {
  gulp.watch('app/**/**.js', ['concat_js_app', 'cache-break']);
  gulp.watch('app/**/**.html', ['templates', 'cache-break']);
  gulp.watch('css/**/**.css', ['concat_css_app', 'cache-break']);
});

gulp.task('serve', function () {
  connect.server({
    port: 8000,
    livereload: true,
    middleware: function (connect, opt) {
      return [
        (function () {
          var url = require('url');
          var proxy = require('proxy-middleware');
          var options = url.parse(API_URL);
          options.route = API_ROUTE;
          return proxy(options);
        })()
      ];
    }
  });
});

gulp.task('default', ['serve', 'watch']);
