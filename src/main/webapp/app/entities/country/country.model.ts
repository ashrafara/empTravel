import { IZone } from 'app/entities/zone/zone.model';

export interface ICountry {
  id?: number;
  name?: string | null;
  zone?: IZone | null;
}

export class Country implements ICountry {
  constructor(public id?: number, public name?: string | null, public zone?: IZone | null) {}
}

export function getCountryIdentifier(country: ICountry): number | undefined {
  return country.id;
}
