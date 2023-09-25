package pl.msiwak.multiplatform.commonResources

import dev.icerock.moko.graphics.Color
import dev.icerock.moko.resources.AssetResource
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.StringResource
import pl.msiwak.multiplatform.commonResources.R

public actual object MR {
  public actual object strings : ResourceContainer<StringResource> {
    public actual val app_name: StringResource = StringResource(R.string.app_name)

    public actual val summary: StringResource = StringResource(R.string.summary)

    public actual val account: StringResource = StringResource(R.string.account)

    public actual val settings: StringResource = StringResource(R.string.settings)

    public actual val summary_add_category: StringResource =
        StringResource(R.string.summary_add_category)

    public actual val email: StringResource = StringResource(R.string.email)

    public actual val password: StringResource = StringResource(R.string.password)

    public actual val register: StringResource = StringResource(R.string.register)

    public actual val login: StringResource = StringResource(R.string.login)

    public actual val yes: StringResource = StringResource(R.string.yes)

    public actual val no: StringResource = StringResource(R.string.no)

    public actual val input_wrong_format: StringResource =
        StringResource(R.string.input_wrong_format)

    public actual val exercise: StringResource = StringResource(R.string.exercise)

    public actual val exercise_name: StringResource = StringResource(R.string.exercise_name)

    public actual val exercise_type: StringResource = StringResource(R.string.exercise_type)

    public actual val add_new_exercise: StringResource = StringResource(R.string.add_new_exercise)

    public actual val category_name: StringResource = StringResource(R.string.category_name)

    public actual val category: StringResource = StringResource(R.string.category)

    public actual val add_new_result: StringResource = StringResource(R.string.add_new_result)

    public actual val remove_result_dialog_title: StringResource =
        StringResource(R.string.remove_result_dialog_title)

    public actual val remove_result_dialog_description: StringResource =
        StringResource(R.string.remove_result_dialog_description)

    public actual val add_result_save: StringResource = StringResource(R.string.add_result_save)

    public actual val settings_language: StringResource = StringResource(R.string.settings_language)

    public actual val settings_unit: StringResource = StringResource(R.string.settings_unit)

    public actual val settings_logout: StringResource = StringResource(R.string.settings_logout)

    public actual val language: StringResource = StringResource(R.string.language)

    public actual val filter_all: StringResource = StringResource(R.string.filter_all)

    public actual val filter_day: StringResource = StringResource(R.string.filter_day)

    public actual val filter_week: StringResource = StringResource(R.string.filter_week)

    public actual val filter_month: StringResource = StringResource(R.string.filter_month)

    public actual val filter_year: StringResource = StringResource(R.string.filter_year)

    public actual val exercise_change_unit: StringResource =
        StringResource(R.string.exercise_change_unit)

    public actual val force_update_title: StringResource =
        StringResource(R.string.force_update_title)

    public actual val force_update_description: StringResource =
        StringResource(R.string.force_update_description)

    public actual val force_update_update: StringResource =
        StringResource(R.string.force_update_update)

    public actual val welcome_no_account: StringResource =
        StringResource(R.string.welcome_no_account)

    public actual val welcome_create_account: StringResource =
        StringResource(R.string.welcome_create_account)

    public actual val welcome_google_login: StringResource =
        StringResource(R.string.welcome_google_login)

    public actual val verify_title: StringResource = StringResource(R.string.verify_title)

    public actual val verify_description: StringResource =
        StringResource(R.string.verify_description)

    public actual val verify_open_mail: StringResource = StringResource(R.string.verify_open_mail)

    public actual val verify_resend_mail: StringResource =
        StringResource(R.string.verify_resend_mail)

    public actual val verify_login: StringResource = StringResource(R.string.verify_login)

    public actual val password_requirements_number: StringResource =
        StringResource(R.string.password_requirements_number)

    public actual val password_requirements_letter: StringResource =
        StringResource(R.string.password_requirements_letter)

    public actual val password_requirements_capital: StringResource =
        StringResource(R.string.password_requirements_capital)

    public actual val password_requirements_special: StringResource =
        StringResource(R.string.password_requirements_special)

    public actual val password_requirements_more_than_eight: StringResource =
        StringResource(R.string.password_requirements_more_than_eight)
  }

  public actual object plurals : ResourceContainer<PluralsResource>

  public actual object images : ResourceContainer<ImageResource> {
    public actual val bg_force_update: ImageResource = ImageResource(R.drawable.bg_force_update)

    public actual val bg_gym: ImageResource = ImageResource(R.drawable.bg_gym)

    public actual val bg_running_field: ImageResource = ImageResource(R.drawable.bg_running_field)
  }

  public actual object fonts : ResourceContainer<FontResource>

  public actual object files : ResourceContainer<FileResource>

  public actual object colors : ResourceContainer<ColorResource> {
    public actual val white: ColorResource = ColorResource(resourceId = R.color.white)

    public actual val black: ColorResource = ColorResource(resourceId = R.color.black)

    public actual val gray: ColorResource = ColorResource(resourceId = R.color.gray)

    public actual val gray_two: ColorResource = ColorResource(resourceId = R.color.gray_two)

    public actual val colorPrimary: ColorResource = ColorResource(resourceId = R.color.colorPrimary)

    public actual val colorOnPrimary: ColorResource = ColorResource(resourceId =
        R.color.colorOnPrimary)

    public actual val colorSecondary: ColorResource = ColorResource(resourceId =
        R.color.colorSecondary)

    public actual val colorOnSecondary: ColorResource = ColorResource(resourceId =
        R.color.colorOnSecondary)

    public actual val colorAccent: ColorResource = ColorResource(resourceId = R.color.colorAccent)

    public actual val colorPrimaryDark: ColorResource = ColorResource(resourceId =
        R.color.colorPrimaryDark)

    public actual val colorSecondaryDark: ColorResource = ColorResource(resourceId =
        R.color.colorSecondaryDark)

    public actual val colorTertiaryDark: ColorResource = ColorResource(resourceId =
        R.color.colorTertiaryDark)

    public actual val colorAccentDark: ColorResource = ColorResource(resourceId =
        R.color.colorAccentDark)
  }

  public actual object assets : ResourceContainer<AssetResource>
}
