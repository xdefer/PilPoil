'use strict';

describe('Controller Tests', function() {

    describe('AdType Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAdType, MockAd;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAdType = jasmine.createSpy('MockAdType');
            MockAd = jasmine.createSpy('MockAd');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AdType': MockAdType,
                'Ad': MockAd
            };
            createController = function() {
                $injector.get('$controller')("AdTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pilpoilApp:adTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
