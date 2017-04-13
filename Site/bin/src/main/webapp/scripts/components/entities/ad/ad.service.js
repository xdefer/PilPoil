'use strict';

angular.module('pilPoilApp')
    .factory('Ad', function ($resource, DateUtils) {
        return $resource('api/ads/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
