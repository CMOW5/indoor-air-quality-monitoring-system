from application.domain.co2.co2_hardware import CO2Hardware
from application.domain.vocs.vocs_hardware import VOCsHardware


class FakeSGP30(CO2Hardware, VOCsHardware):
    """
     A fake implementation of the SGP30 sensor (eco2 & tvocs) to be used for testing purposes.
     we're just increasing the variable by x_step to generate fake data
    """

    def __init__(self):
        self.co2 = 0
        self.min_co2 = 400
        self.max_co2 = 60000
        self.co2_step = 500

        self.tvocs = 0
        self.min_tvocs = 0
        self.max_tvocs = 60000
        self.tvocs_step = 1000

    def read_co2(self) -> float:
        self.co2 = self.min_co2 if self.co2 == self.max_co2 \
                                else self.co2 + self.co2_step

        return self.co2

    def read_tvocs(self) -> float:
        self.tvocs = self.min_tvocs if self.tvocs == self.max_tvocs \
                                    else self.tvocs + self.tvocs_step

        return self.co2

