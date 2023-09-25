package pl.msiwak.multiplatform.commonResources

import dev.icerock.moko.resources.AssetResource
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.StringResource

public expect object MR {
  public object strings : ResourceContainer<StringResource> {
    public val app_name: StringResource

    public val summary: StringResource

    public val account: StringResource

    public val settings: StringResource

    public val summary_add_category: StringResource

    public val email: StringResource

    public val password: StringResource

    public val register: StringResource

    public val login: StringResource

    public val yes: StringResource

    public val no: StringResource

    public val input_wrong_format: StringResource

    public val exercise: StringResource

    public val exercise_name: StringResource

    public val exercise_type: StringResource

    public val add_new_exercise: StringResource

    public val category_name: StringResource

    public val category: StringResource

    public val add_new_result: StringResource

    public val remove_result_dialog_title: StringResource

    public val remove_result_dialog_description: StringResource

    public val add_result_save: StringResource

    public val settings_language: StringResource

    public val settings_unit: StringResource

    public val settings_logout: StringResource

    public val language: StringResource

    public val filter_all: StringResource

    public val filter_day: StringResource

    public val filter_week: StringResource

    public val filter_month: StringResource

    public val filter_year: StringResource

    public val exercise_change_unit: StringResource

    public val force_update_title: StringResource

    public val force_update_description: StringResource

    public val force_update_update: StringResource

    public val welcome_no_account: StringResource

    public val welcome_create_account: StringResource

    public val welcome_google_login: StringResource

    public val verify_title: StringResource

    public val verify_description: StringResource

    public val verify_open_mail: StringResource

    public val verify_resend_mail: StringResource

    public val verify_login: StringResource

    public val password_requirements_number: StringResource

    public val password_requirements_letter: StringResource

    public val password_requirements_capital: StringResource

    public val password_requirements_special: StringResource

    public val password_requirements_more_than_eight: StringResource
  }

  public object plurals : ResourceContainer<PluralsResource>

  public object images : ResourceContainer<ImageResource> {
    public val bg_force_update: ImageResource

    public val bg_gym: ImageResource

    public val bg_running_field: ImageResource
  }

  public object fonts : ResourceContainer<FontResource>

  public object files : ResourceContainer<FileResource>

  public object colors : ResourceContainer<ColorResource> {
    public val white: ColorResource

    public val black: ColorResource

    public val gray: ColorResource

    public val gray_two: ColorResource

    public val colorPrimary: ColorResource

    public val colorOnPrimary: ColorResource

    public val colorSecondary: ColorResource

    public val colorOnSecondary: ColorResource

    public val colorAccent: ColorResource

    public val colorPrimaryDark: ColorResource

    public val colorSecondaryDark: ColorResource

    public val colorTertiaryDark: ColorResource

    public val colorAccentDark: ColorResource
  }

  public object assets : ResourceContainer<AssetResource>
}
