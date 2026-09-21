package com.github.livreprogramacao.registeringavehicle.domain.model;

/**
 * **CaseReference** usually means the **reference number assigned to your vehicle-registration application or case** by the registration authority.
 *
 * For example, it might look like:
 *
 * - `CR-2026-001234`
 * - `REG12345678`
 * - `A7B9C2D1`
 *
 * It may be used to:
 *
 * - Track your registration application
 * - Contact the registration office
 * - Check the status of a pending request
 * - Identify a correction, transfer, or renewal case
 *
 * It is **not usually the same as the VIN** or the vehicle registration/plate number. If the field appears on an official registration document, use the exact number shown next to **CaseReference** or **Case Reference**.
 */
public class CaseReference {

    final String reference;

    public CaseReference( String reference ) {

        this.reference = reference;

    }

    static public CaseReference of( String value ) {

        // TODO
        return new CaseReference( value );

    }

    static public CaseReference value() {

        // TODO
        return new CaseReference( "REG12345678" );

    }

    static public CaseReference reference() {

        // TODO
        return new CaseReference( "REG12345678" );

    }

}
