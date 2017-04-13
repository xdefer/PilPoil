'use strict';

angular.module('pilpoilwebApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


