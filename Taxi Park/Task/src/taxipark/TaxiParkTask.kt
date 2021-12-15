package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    this.allDrivers.filter{d-> trips.none {it.driver == d}}.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    this.allPassengers
        .filter {
            this.trips
                .count{ trip: Trip -> it in trip.passengers}>=minTrips}
        .toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers
            .filter { this.trips.count {trip: Trip -> it in trip.passengers && trip.driver == driver}>1 }
            .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers
            .filter {p:Passenger ->
                this.trips.count { t: Trip -> p in t.passengers && t.discount!=null}>
                this.trips.count { t:Trip -> p in t.passengers &&t.discount == null} }
            .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if(trips.isEmpty()) {
        return null
    }
    else {
        val maxDuration: Int = trips.map{it.duration}.max() ?: 0
        val mapByNumsOfTrips = HashMap<Int, IntRange>()
        for(i in 0..maxDuration step 10){
            val range = IntRange(i, i + 9)
            val numOfTripsInThisRange = this.trips.filter{ it.duration in range}.count()
            mapByNumsOfTrips[numOfTripsInThisRange]=range
        }
        return mapByNumsOfTrips[mapByNumsOfTrips.toSortedMap().lastKey()]
    }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if(trips.isEmpty()) {
        return false
    }
    else {
        val TotalTripsCosts = this.trips.map { it.cost }.sum()
        val mapCostByDriverSorted= trips
            .groupBy { it.driver }
            .mapValues { (_,trips) -> trips.sumByDouble { it.cost }}
            .toList()
            .sortedByDescending { (_,value) -> value }.toMap()
        var currentSum = 0.0
        var driversSize = 0
        for(value in mapCostByDriverSorted.values) {
            driversSize++
            currentSum += value
            if (currentSum >= (TotalTripsCosts * 0.8)) break
        }
        return driversSize <= (allDrivers.size * 0.2)
    }
}