'use strict';

angular.module('pilPoilApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


