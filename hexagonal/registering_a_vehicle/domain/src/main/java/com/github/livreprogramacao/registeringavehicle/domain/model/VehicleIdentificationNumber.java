package com.github.livreprogramacao.registeringavehicle.domain.model;

/**
 * “VIN” means **Vehicle Identification Number**. It’s a unique 17-character code assigned to a vehicle, similar to a fingerprint.
 *
 * On a vehicle registration, it identifies the specific car and may be used to verify:
 *
 * - Make, model, and year
 * - Ownership and registration records
 * - Insurance and accident history
 * - Recalls or theft reports
 *
 * You can usually find the VIN on the dashboard near the windshield, the driver-side door frame, the title, or insurance documents.
 * <br/>
 * Examples of VINs:
 *
 * - `1HGCM82633A123456`
 * - `5YFBURHE5FP456789`
 * - `JTDKN3DU0A0123456`
 *
 * A VIN usually has **17 letters and numbers**. For example, in `1HGCM82633A123456`:
 *
 * - `1` = country/region where it was manufactured
 * - `HG` = manufacturer
 * - `CM826` = vehicle description
 * - `3` = check digit
 * - `3` = model year code
 * - `A` = manufacturing plant
 * - `123456` = vehicle’s unique serial number
 *
 * VIN formats vary by manufacturer, so the exact meaning of each section can differ. The letters **I, O, and Q** are generally not used because they can be confused with `1` and `0`.
 */
public class VehicleIdentificationNumber {

    private final String code; // = "1HGCM82633A123456";

    private VehicleIdentificationNumber( String value ) {
        this.code = value;
    }

    static public VehicleIdentificationNumber of( String value ) {

        // TODO
        return new VehicleIdentificationNumber( value );

    }

    static public VehicleIdentificationNumber value() {

        // TODO
        return new VehicleIdentificationNumber( "1HGCM82633A123456" );

    }

}
