(function(){
    angular.module('MyApp')
        .factory('DuplicateService', DuplicateService);

    function DuplicateService($http) {
        var api = {
            "getDuplicates": getDuplicates,
            "getUnique": getUnique
        };

        function getDuplicates(){
            var url = "/api/duplicates";
            return $http.get(url);
        }

        function getUnique(){
            var url = "/api/unique";
            return $http.get(url);
        }
        return api;

    }
})();