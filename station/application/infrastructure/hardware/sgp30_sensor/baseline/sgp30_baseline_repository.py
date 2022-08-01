from typing import Tuple


class SGP30Repository:
    BASELINE_FILE = '/home/pi/project-vector/iaq_repo/iaq_baseline.txt'

    BASELINE_SEPARATOR = ','

    def get_iaq_baseline(self) -> Tuple[int, int]:
        with open(SGP30Repository.BASELINE_FILE, 'a+') as file:
            file.seek(0)  # need this to always read from the beginning of the file
            lines = file.read().split(SGP30Repository.BASELINE_SEPARATOR)
            eco2_baseline, tvocs_baseline = list(map(int, lines))  # convert the list of str to a list of int
            self.eco2_baseline = eco2_baseline
            self.tvocs_baseline = tvocs_baseline

        return self.eco2_baseline, self.tvocs_baseline

    def save_iaq_baseline(self, eco2_baseline: int, tvocs_baseline: int):
        self.eco2_baseline = eco2_baseline
        self.tvocs_baseline = tvocs_baseline

        content = "{eco2_baseline},{tvocs_baseline}".format(eco2_baseline=eco2_baseline, tvocs_baseline=tvocs_baseline)

        try:
            with open(SGP30Repository.BASELINE_FILE, 'w+') as file:
                file.write(content)
        except FileNotFoundError as error:
            print('WARNING, something wrong happened while saving the IAQ_BASELINE, exception is = ', error)
