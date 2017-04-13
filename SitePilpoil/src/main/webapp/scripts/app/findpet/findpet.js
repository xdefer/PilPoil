'use strict';

angular.module('pilpoilApp').config(function ($stateProvider) {
	$stateProvider.state('findPet', {
    	parent: 'site',
         url: '/findPet',
         data: {
         	authorities: []
         },
         views: {
        	 'content@': {
        		 templateUrl: 'scripts/app/findpet/findpet.html',
                 controller: 'FindpetController'
        	 }
         },
         resolve: {
        	 mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
        		 $translatePartialLoader.addPart('findpet');
        		 $translatePartialLoader.addPart('ad');
                 return $translate.refresh();
              }]
         }
	})
});
