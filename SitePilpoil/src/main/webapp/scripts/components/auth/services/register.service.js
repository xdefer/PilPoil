'use strict';

angular.module('pilpoilApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


