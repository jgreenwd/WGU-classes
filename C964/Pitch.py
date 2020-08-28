PITCH_KEYS = {'CH': 0, 'CU': 1,  'FC': 2,  'FF': 3,  'FT': 4,  'SI': 5,  'SL': 6,  'FS': 7,  'KC': 8,
              'KN': 9, 'EP': 10, 'FO': 11, 'SC': 12, 'FA': 13, 'AB': 14, 'PO': 15, 'IN': 16, 'UN': 17}

PITCH_KEYS_REVERSE = {v: k for k, v in PITCH_KEYS.items()}

PITCH_LABELS = {'CH': 'Changeup',  'CU': 'Curveball',  'FC': 'Cutter',  'FF': '4-seam Fastball',  'FS': 'Splitter',
                'FT': '2-seam Fastball',  'KC': 'Knucklecurve',  'SI': 'Sinker',  'SL': 'Slider', 'KN': 'Knuckleball',
                'EP': 'Eephus', 'FO': 'Forkball', 'SC': 'Screwball', 'FA': 'Fastball', 'AB': 'Atbat', 'PO': 'Pitchout',
                'IN': 'Intentional Walk', 'UN': 'Unknown'}

STD_PITCHES_REMOVED = ['UN', 'IN', 'PO', 'FA', 'AB', 'EP', 'FO', 'KN', 'SC', 'KC', 'FS']


class Pitch:
    def __init__(self, x0=-0.69, y0=50, z0=5.786, vx0=2.21, vy0=-128, vz0=-4.35, ax=-2.21, ay=26.2, az=-22.8):
        """ Given requisite telemetry, calculate pfx_x & pfx_z for multi-classification """
        from math import sqrt

        self.x0 = x0
        self.y0 = y0
        self.z0 = z0
        self.vx0 = vx0
        self.vy0 = vy0
        self.vz0 = vz0
        self.ax = ax
        self.ay = ay
        self.az = az
        self.p_throws = None
        self.start_speed = None
        self.end_speed = None
        self.spin_dir = None
        self.spin_rate = None
        self.break_angle = None
        self.break_length = None
        self.break_y = None

        self._pitch_type = None
        self._vyf = -1 * sqrt(vy0 ** 2 + (2 * ay * (y0 - 1.417)))
        self._T = (self._vyf - vy0) / ay
        self._vx = vx0 + ((ax * self._T) / 2)
        self._vy = (self._vyf + vy0) / 2
        self._vz = vz0 + ((az * self._T) / 2)
        self._axm = ax - (ay * (self._vx / self._vy))
        self._azm = az - (ay * (self._vz / self._vy)) + 32.174
        self._pfx_x = (self._axm * self._T ** 2) / 2
        self._pfx_z = (self._azm * self._T ** 2) / 2

    def set_secondary_attr(self, p_throws=None, start_spd=None, end_spd=None, spin_dir=None, spin_rate=None,
                           break_angle=None, break_length=None, break_y=None):
        self.p_throws = p_throws
        self.start_speed = start_spd
        self.end_speed = end_spd
        self.spin_dir = spin_dir
        self.spin_rate = spin_rate
        self.break_angle = break_angle
        self.break_length = break_length
        self.break_y = break_y

    def set_pitch_type(self, pitch_type):
        """ (Int) pitch multi-classification result """
        self._pitch_type = pitch_type

    def get_pitch_type(self):
        """ (Int) pitch multi-classification result """
        return self._pitch_type

    def get_pfx_x(self):
        return self._pfx_x

    def get_pfx_z(self):
        return self._pfx_z
