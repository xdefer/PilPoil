'use strict';

angular.module('pilpoilApp').factory('Animal', function ($resource, DateUtils) {
	return $resource('api/animals/:id', {}, {
		'query': { method: 'GET', isArray: true},
        'get': {
            method: 'GET',
            transformResponse: function (data){
            	data = angular.fromJson(data);
                return data;
            }
        },
        'update': { method:'PUT' }
    });
}).factory('AnimalByUser', function ($resource, DateUtils) {
    return $resource('api/animals/user/:id', {}, {
        'get': { method: 'GET', isArray: true}
    });
});

