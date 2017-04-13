'use strict';

angular.module('pilpoilApp')
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
    }).factory('AdsByAnimal', function($resource, DateUtils){
    	return $resource('api/ads/animal/:id', {}, {
    		'get' : {method: 'GET', isArray: true}
    	});
    }).factory('ArchiveAd', function($resource, DateUtils){
    	return $resource('api/ads/archive/:id', {}, {
    		'update' : {method: 'PUT'}
    	});
    });